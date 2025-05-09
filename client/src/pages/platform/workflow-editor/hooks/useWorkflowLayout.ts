import useRightSidebarStore from '@/pages/platform/workflow-editor/stores/useRightSidebarStore';
import useWorkflowDataStore from '@/pages/platform/workflow-editor/stores/useWorkflowDataStore';
import useWorkflowEditorStore from '@/pages/platform/workflow-editor/stores/useWorkflowEditorStore';
import useWorkflowNodeDetailsPanelStore from '@/pages/platform/workflow-editor/stores/useWorkflowNodeDetailsPanelStore';
import useWorkflowTestChatStore from '@/pages/platform/workflow-editor/stores/useWorkflowTestChatStore';
import {Source, useCopilotStore} from '@/shared/components/copilot/stores/useCopilotStore';
import {ComponentDefinitionBasic, WorkflowNodeOutput} from '@/shared/middleware/platform/configuration';
import {useGetComponentDefinitionsQuery} from '@/shared/queries/platform/componentDefinitions.queries';
import {useGetTaskDispatcherDefinitionsQuery} from '@/shared/queries/platform/taskDispatcherDefinitions.queries';
import {useGetPreviousWorkflowNodeOutputsQuery} from '@/shared/queries/platform/workflowNodeOutputs.queries';
import {useGetWorkflowTestConfigurationQuery} from '@/shared/queries/platform/workflowTestConfigurations.queries';
import {useEffect, useMemo} from 'react';

export const useWorkflowLayout = () => {
    const {copilotPanelOpen, setContext, setCopilotPanelOpen} = useCopilotStore();
    const {rightSidebarOpen, setRightSidebarOpen} = useRightSidebarStore();
    const {componentActions, setComponentDefinitions, setTaskDispatcherDefinitions, workflow} = useWorkflowDataStore();
    const {setShowWorkflowCodeEditorSheet, setShowWorkflowInputsSheet, setShowWorkflowOutputsSheet} =
        useWorkflowEditorStore();
    const {currentNode, setWorkflowNodeDetailsPanelOpen} = useWorkflowNodeDetailsPanelStore();
    const {setWorkflowTestChatPanelOpen} = useWorkflowTestChatStore();

    const {data: workflowTestConfiguration} = useGetWorkflowTestConfigurationQuery({workflowId: workflow.id!});

    const workflowTestConfigurationConnections = useMemo(
        () =>
            (workflowTestConfiguration?.connections ?? []).reduce(
                (map: {[key: string]: number}, workflowTestConfigurationConnection) => {
                    const {connectionId, workflowConnectionKey, workflowNodeName} = workflowTestConfigurationConnection;

                    map[`${workflowNodeName}_${workflowConnectionKey}`] = connectionId;

                    return map;
                },
                {}
            ),
        [workflowTestConfiguration]
    );

    const workflowTestConfigurationInputs = useMemo(
        () => workflowTestConfiguration?.inputs ?? {},
        [workflowTestConfiguration]
    );

    const runDisabled = useMemo(() => {
        const requiredInputsMissing = (workflow?.inputs ?? []).some(
            (input) => input.required && !workflowTestConfigurationInputs[input.name]
        );

        const noTasks = (workflow?.tasks ?? []).length === 0;

        const requiredConnectionsMissing = (workflow?.tasks ?? [])
            .flatMap((task) => task.connections ?? [])
            .some(
                (workflowConnection) =>
                    workflowConnection.required &&
                    !workflowTestConfigurationConnections[
                        `${workflowConnection.workflowNodeName}_${workflowConnection.key}`
                    ]
            );

        return requiredInputsMissing || noTasks || requiredConnectionsMissing;
    }, [workflow, workflowTestConfigurationInputs, workflowTestConfigurationConnections]);

    const testConfigurationDisabled = useMemo(() => {
        const noInputs = (workflow?.inputs ?? []).length === 0;

        const noConnections =
            [...(workflow?.triggers ?? []), ...(workflow?.tasks ?? [])].flatMap(
                (operation) => operation.connections ?? []
            ).length === 0;

        return noInputs && noConnections;
    }, [workflow]);

    const {
        data: componentDefinitions,
        error: componentsError,
        isLoading: componentsIsLoading,
    } = useGetComponentDefinitionsQuery({
        actionDefinitions: true,
        triggerDefinitions: true,
    });

    const {
        data: taskDispatcherDefinitions,
        error: taskDispatcherDefinitionsError,
        isLoading: taskDispatcherDefinitionsLoading,
    } = useGetTaskDispatcherDefinitionsQuery();

    const {data: workflowNodeOutputs, isPending: isWorkflowNodeOutputsPending} = useGetPreviousWorkflowNodeOutputsQuery(
        {
            id: workflow.id!,
            lastWorkflowNodeName: currentNode?.name,
        },
        !!componentActions?.length && !!currentNode && !!currentNode?.name && !currentNode?.trigger
    );

    let previousComponentDefinitions: ComponentDefinitionBasic[] = [];

    let filteredWorkflowNodeOutputs;

    if (!currentNode?.trigger && workflowNodeOutputs && componentDefinitions) {
        const definitionsMap = new Map(
            [...componentDefinitions, ...(taskDispatcherDefinitions ?? [])].map((def) => [def.name, def])
        );

        const result = workflowNodeOutputs.reduce(
            (acc, output) => {
                const {actionDefinition, taskDispatcherDefinition, triggerDefinition} = output;

                if (!actionDefinition && !triggerDefinition && taskDispatcherDefinition?.name !== 'loop') {
                    return acc;
                }

                let componentName: string | undefined;

                if (actionDefinition?.componentName) {
                    componentName = actionDefinition.componentName;
                } else if (triggerDefinition?.componentName) {
                    componentName = triggerDefinition.componentName;
                } else if (taskDispatcherDefinition?.name === 'loop') {
                    componentName = 'loop';
                }

                const matchingDefinition = componentName ? definitionsMap.get(componentName) : undefined;

                if (matchingDefinition) {
                    acc.definitions.push(matchingDefinition);

                    acc.outputs.push(output);
                }

                return acc;
            },
            {definitions: [] as ComponentDefinitionBasic[], outputs: [] as WorkflowNodeOutput[]}
        );

        previousComponentDefinitions = result.definitions;
        filteredWorkflowNodeOutputs = result.outputs;
    }

    const handleComponentsAndFlowControlsClick = () => {
        setWorkflowNodeDetailsPanelOpen(false);
        setWorkflowTestChatPanelOpen(false);
        setRightSidebarOpen(!rightSidebarOpen);
    };

    const handleCopilotClick = () => {
        setContext({parameters: {}, source: Source.WORKFLOW_EDITOR});

        if (!copilotPanelOpen) {
            setCopilotPanelOpen(!copilotPanelOpen);
        }
    };

    const handleWorkflowCodeEditorClick = () => setShowWorkflowCodeEditorSheet(true);

    const handleWorkflowInputsClick = () => setShowWorkflowInputsSheet(true);

    const handleWorkflowOutputsClick = () => setShowWorkflowOutputsSheet(true);

    useEffect(() => {
        if (componentDefinitions) {
            setComponentDefinitions(componentDefinitions);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [componentDefinitions?.length]);

    useEffect(() => {
        if (taskDispatcherDefinitions) {
            setTaskDispatcherDefinitions(taskDispatcherDefinitions);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [taskDispatcherDefinitions?.length]);

    return {
        componentDefinitions,
        componentsError,
        componentsIsLoading,
        filteredWorkflowNodeOutputs,
        handleComponentsAndFlowControlsClick,
        handleCopilotClick,
        handleWorkflowCodeEditorClick,
        handleWorkflowInputsClick,
        handleWorkflowOutputsClick,
        isWorkflowNodeOutputsPending,
        previousComponentDefinitions,
        runDisabled,
        taskDispatcherDefinitions,
        taskDispatcherDefinitionsError,
        taskDispatcherDefinitionsLoading,
        testConfigurationDisabled,
        workflowTestConfiguration,
    };
};
